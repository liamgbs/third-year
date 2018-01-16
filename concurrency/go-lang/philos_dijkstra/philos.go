package main

import (
    "fmt"
    "math/rand"
    "time"
)

const philosophers = 5

// A fork object in the program models a physical fork in the simulation.
// A separate channel represents each fork place.  Two philosophers
// have access to each fork.  The channels are buffered with capacity = 1,
// representing a place for a single fork.

func think() {
    random_int := rand.Intn(3) + 1
    time.Sleep(time.Second * time.Duration(random_int))
}

// Eating and thinking are the same process
// Define the alias 'eat' for code clarity
func eat() { think() }

func philosopher(id int, left_hand, right_hand chan bool) {
    for {
        fmt.Println("philosopher", id, "is thinking")
        think()
        <-left_hand
        <-right_hand
        fmt.Println("philosopher", id, "is eating")
        eat()
        left_hand <- true
        right_hand <- true
    }
}

func main() {
    // Create fork channels and start philosophers up
    last_fork := make(chan bool, 1)
    last_fork <- true
    fork_left := last_fork
    for i := 0; i < philosophers - 1; i++ {
        fork_right := make(chan bool, 1)
        fork_right <- true
        go philosopher(i, fork_left, fork_right)
        // Make this philosophers right fork the next philosophers left fork
        fork_left = fork_right
    }

    // Make last philosopher left handed
    go philosopher(philosophers - 1, last_fork, fork_left)

    for {}
}
