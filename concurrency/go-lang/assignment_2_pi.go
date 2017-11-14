package main

import "fmt"
import "math/rand"
import "time"

func worker(result chan float64, darts_thrown int) {
  hitcount := 0
  // Create a random number generator using current time as a seed
  random_src := rand.NewSource(time.Now().UnixNano())
  random := rand.New(random_src)

  for i := 0; i < darts_thrown; i++ {
    // Throw the dart
    x := random.Float64()
    y := random.Float64()

    if ((x*x) + (y*y)) < 1.0 {
      hitcount++
    }
  }
  var partial_pi float64 = 4.0 * float64(hitcount) / float64(darts_thrown)
  result <- partial_pi
}

func main() {
  threads := 4
  darts_thrown := 2000000
  result := make(chan float64, threads)
  var pi float64

  for i := 0; i < threads; i++ {
    go worker(result, darts_thrown / threads)
  }

  for j := 0; j < threads; j++ {
    pi = pi + <- result
  }

  pi = pi / float64(threads)

  fmt.Println("Estimated pi:", pi)
  fmt.Println("Actual pi:", 3.1415926535)
}
