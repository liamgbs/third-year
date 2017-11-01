// tells js to execute in strict mode meaning js will
// die when it comes across certain coding no-nos
"use strict";
// for example the below statements will cause errors (there are more cases)
x = 1; // need to use var
delete x; // cant delete
function x(a,a){}; // cant use identical param names
function yield(){}; // cant use reserved keyword "yield"

// DECLARING VARIABLES

// var adds the variable as a property on global window object
var foo = "bar";
// let does not
let foo = "bar";
// const is constant value, like other languages
const MAX = 10;

// TYPES

// strings are either double or single quotes, doesnt matter.
// string are concatenated with + operator (or String.concat() method)
var s = "im a string" + ' im also a string' // returns "im a string im also a string"
// no ints or floats in js, just numbers. All below are same type to js.
var decimal = 1.1, // separate multiple varables declarations with comma
    integer = 20,
    science = 1.1e1,
    break_universe = Infinity; // yep, Infinity is a number
// booleans are lowercase true or false
var yesno = true,
    noyes = false;
// null is just null but it is a primitive js type
var doesnt_exist = null;
// undefined is not null, but its also a primitive
var also_doesnt_exist = undefined;
// most things in js are objects, except the above
var obj = Object;
// objects have methods and properties which you can define
// and redefine at your leisure (even the built in ones)
obj.hello = "world";

// Arrays are built in objects, they have properties and methods
var list_of_primes = [2, 3, 5, 7, 11, 13];
list_of_primes.length; // 6
list_of_primes.push(5);
// They use zero based indexing
list_of_primes[0] = 17;
// And support keys
list_of_primes["java"] = "script";
// Define empty arrays like this
var arr_data = Array(10)
// Use for loop to fill array with 1-10
for (var i = 1; i <= arr_data.length; i++) {
  arr_data[i-1] = i;
}
// useful functions for arrays
var mapped = arr_data.map( (number) => number * 2 ); // returns [ 2, 4, 6, 8, 10, 12, 14, 16, 18, 20 ]
var filtered = arr_data.filter( (number) => number % 2 ); // returns [ 1, 3, 5, 7, 9 ]
var reduced = arr_data.reduce( (sum, number) => sum * number ); // returns 7257600

// define function the normal way as an anonymous function
// function declaration
function hello() {
  return "hello";
}

// function expression
let add = function(x, y) {
  return x+y;
}; // note the semi colon

// function expressions allow arrow notation
// the below defines a function to double x
// single parameters dont need parentheses
let double = x => x+x;
// better practice to put the parameters in parentheses though...
let double = (x) => x+x;
// braces allow more lines
let x = () => {
  console.log("do something");
  console.log("do something else");
}

// function(){} is a statement
// (function(){}); is an expression
// we can invoke the function immediately and we dont have to name it at all
(function(x, y) {
  return x+y;
})(1,2); // returns 3
// Or using arrow notation:
((x, y) => x+y)(5,5);// returns 10

// {} creates a new object
let createObject = function(data) {
  return {"data": data, // defines a property
          someMethod: function(){return "something"} // defines a function
        };
}
// or...
let createObject = (data) => {return {"data": data}};
// data is now a property of the returned object
let obj = createObject("some_data");
// access with .
console.log(obj.data); // console.log() prints to, well, the console.

// we can access the arguments using "arguments" which is an array
function foo(a, b, c) {
  console.log(arguments.length); // would print 3 to console
}

// function factories
// we can have function which return functions where the parameter is bound
function die(sides) {
  return function() {
    return {Math.random() * sides}
  }
}
var roll5 = die(5);
roll5(); // returns random die side

// Partial functions
// Useful when you want to do things in steps
function die(sides, number) {
  if (arguments.length < 1) {
    return die;
  }
  else if (arguments.length == 1) {
    return function(number) {
      return die(sides, number);
    }
  }
  else {
    return function() {
      var total = 0;
      for (var i=0; i<number; i++)
        total += Math.ceil(Math.random() * sides);
      return total;
    }
  }
}

var d6 = die(6);
var result1 = d6(1)();
console.log(result1);
var d6s1 = die(6, 1);
var result2 = d6s1();
console.log(result2);

// PSEUDO CLASSES
// 'this' keyword refers to object the function is contained by
function User(login, password) {
  this["login"] = login;
  this["password"] = password;
}
// be careful using 'this' though...
// the below will treat 'this' as the root window object
var user = User("liam", "secure_password");
// we need to use the 'new' keyword like so
var user = new User("liam", "secure_password");

// the above is ugly though and not what we're all used to so...
class User {
  constructor(login, password) {
    this.login = login;
    this.password = password;
  }
  toString() { // overrides the default toString method for the object
    return this.login + this.password;
  }
}
// Using this format is only syntactic sugar, and both methods run the same
// Create the object the same way. DONT FORGET THE 'new'
var user = new User("liam", "secure_password");

// inheritance works from the object created, not the "class"
var SuperUser = Object.create(user);
// But you can also use extends in the class syntax
class SuperUser extends User {
  constructor(login, password, something_else) {
    // Can also use the super keyword to call the superclass
    super(login, password);
    this.something = something_else;
  }
}

// We can also use "class attributes", but theyre actually static variables for the object
var Car = function(reg_no) {
  this.reg_no = reg_no;
  Car.count++;
}

// Closures
// Using a closure we can keep the scope of an object even when we leave the scope ourselves
var timer = function() {
  var start = new Date().getTime(); // scopes start to outer body but the returned function can still access it
  return function () {
    return new Date().getTime() - start;
  };
};
var time_it = timer();
console.log(time_it()); // 10ms
console.log("wait 10 seconds");
console.log(time_it()); // 10000ms
