package main

import (
  "fmt"
  "math/rand"
  "time"
)

const philosophers = 5

type fork struct {}

func think() {
    random_int := rand.Intn(3) + 1
    time.Sleep(time.Second * time.Duration(random_int))
}

func eat() { think() }

func main() {
  forks := make([]chan fork, philosophers)

  for i := 0; i < philosophers; i++ {
    // channels are buffered with 1 since only one fork can be on the channel at once
    forks[i] = make(chan fork, 1)
  }

  go philosopher(0, forks[philosophers - 1], forks[0])
  for j := 1; j < philosophers; j++ {
    go philosopher(j, forks[j-1], forks[j])
  }

  for {}

}

func philosopher(id int, left_neighbour chan fork, right_neighbour chan fork) {
  var f_left fork
  var f_right fork

  // clean initial dirty fork and put it on the left
  left_neighbour <- f_left

  for {
    fmt.Println("Philosopher", id, "is thinking")
    think()

    // Get forks from table when available
    f_right = <- right_neighbour
    f_left = <- left_neighbour

    fmt.Println("Philosopher", id, "is eating")
    eat()

    // Clean forks and put the back on the table
    left_neighbour <- f_left
    right_neighbour <- f_right

  }
}
