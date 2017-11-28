package main

import "fmt"
import "math/rand"
import "time"

const num_instructions = 100
const num_pipelines = 3

func generateInstructions(opcodes chan int) {
  for i := 0; i < num_instructions; i++ {
    random_int := rand.Intn(4) + 1
    opcodes <- random_int
  }
}

func dispatcher(opcodes chan int, to_execute []chan int, ready_for_next []chan int) {
  for i := 0; i < num_pipelines; i++ {
    fmt.Println("Dispatching")
    op := <- opcodes
    to_execute[i] <- op
  }

  for {
    select {
    case <- ready_for_next[0]:
      op := <- opcodes
      to_execute[0] <- op
    case <- ready_for_next[1]:
      op := <- opcodes
      to_execute[1] <- op
    case <- ready_for_next[2]:
      op := <- opcodes
      to_execute[2] <- op
    }
  }
}

func executeInstructions(to_execute chan int, ready_for_next chan int, to_retire chan int) {
  for {
    instruction := <- to_execute
    fmt.Println("hello")
    fmt.Println("sleeping for:", instruction)
    time.Sleep(time.Second * time.Duration(instruction))
    fmt.Println("woken after:", instruction)
    to_retire <- instruction
    ready_for_next <- 1
  }
}

func retire(to_retire []chan int) {
  for {
    fmt.Println("retiring")
    select {
    case inst1 := <- to_retire[0]:
      fmt.Println("channel 0:", inst1)
    case inst2 := <- to_retire[1]:
      fmt.Println("channel 1:", inst2)
    case inst3 := <- to_retire[2]:
      fmt.Println("channel 2:", inst3)
    }
  }
}


func main() {
  rand.Seed(time.Now().Unix())
  opcodes := make(chan int, num_instructions)
  to_execute := make([]chan int, num_pipelines)
  to_retire := make([]chan int, num_pipelines)
  ready_for_next := make([]chan int, num_pipelines)

  go retire(to_retire)

  for i := 0; i < num_pipelines; i++ {
    go executeInstructions(to_execute[i], ready_for_next[i], to_retire[i])
  }

  go dispatcher(opcodes, to_execute, ready_for_next)
  go generateInstructions(opcodes)



  for {}
}
