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
  partial_pi := 4.0 * float64(hitcount) / float64(darts_thrown)
  // send partial_pi to the channel (we dont care about the order they arrive in
  // since theyre all sum reduced anyway).
  result <- partial_pi
}

func main() {
  threads := 4
  darts_thrown := 2000000
  // create a buffered channel where the buffer amount is equal to threads
  // ie. we can have thread number of values waiting to be read from the channel
  result := make(chan float64, threads)
  var pi float64

  // Spawn thread number of worker threads all running concurrently
  for i := 0; i < threads; i++ {
    go worker(result, darts_thrown / threads)
  }

  // take all the buffered values from the channel and add them together
  for j := 0; j < threads; j++ {
    pi = pi + <- result
  }

  // divide the sum of all thread results by the number of threads to get pi
  pi = pi / float64(threads)

  fmt.Println("Estimated pi:", pi)
  fmt.Println("Actual pi:", 3.1415926535)
}
