package main

import "fmt"
import "time"
import "math/rand"

const threads = 4
const darts_to_throw = 100000000
const work_to_do = 10000

func main() {
  // Create arrays of thread number of channels of type int
  // for both results and ready for more work
  result := make([]chan int, threads)
  workload := make([]chan int, threads)
  hitcount := 0
  darts_thrown := 0

  start_time := time.Now()

  for i := 0; i < threads; i++ {
    result[i] = make(chan int)
    workload[i] = make(chan int)
  }

  for i := 0; i < threads; i++ {
    go worker(result[i], workload[i])
  }

  for i := 0; i < threads; i++ {
    workload[i] <- work_to_do
    darts_thrown += work_to_do
  }

  for {
    select {
    case r_1 := <- result[0]:
      // Take result from result[i] and add to hitcount
      hitcount += r_1
      // Give the thread more work
      workload[0] <- work_to_do
    case r_2 := <- result[1]:
      hitcount += r_2
      workload[1] <- work_to_do
    case r_3 := <- result[2]:
      hitcount += r_3
      workload[2] <- work_to_do
    case r_4 := <- result[3]:
      hitcount += r_4
      workload[3] <- work_to_do
    }

    // Keep track of how many darts thrown overall
    darts_thrown += work_to_do
    // If d_t is < 0 then no more work needs doing so we dont care about the
    // result anymore so just break out of loop
    if darts_thrown >= darts_to_throw {
      break
    }
  }

  pi := 4.0 * float64(hitcount) / float64(darts_thrown)

  elapsed := time.Since(start_time)

  fmt.Println("Number of threads:", threads)
  fmt.Println("Number of throws:", darts_thrown)
  fmt.Println("Hitcount:", hitcount)
  fmt.Println("Time elapsed:", elapsed)
  fmt.Println("Estimated pi:", pi)
  fmt.Println("Actual pi:", 3.1415926535)
}

func worker(result chan int, workload chan int) {
  // Create a random number generator using current time as a seed
  random_src := rand.NewSource(time.Now().UnixNano())
  random := rand.New(random_src)
  to_throw := 0

  for {
    // Take the new workload off the workload channel
    to_throw = <- workload
    // Calculate result
    hitcount := 0
    for i := 0; i < to_throw; i++ {
      // Throw the dart
      x := random.Float64()
      y := random.Float64()

      if ((x*x) + (y*y)) < 1.0 {
        hitcount++
      }
    }

    // Send result back to farmer
    result <- hitcount
  }
}
