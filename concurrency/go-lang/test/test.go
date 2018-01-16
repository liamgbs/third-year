package main

import "fmt"

func test() {
  for {
    fmt.Println("hello")
  }
}

func main() {
  go test()
}
