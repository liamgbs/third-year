; Everything is in brackets!
; expressions are like polish notation
(* 5 5) ; 25
(+ 10 90) ; 100
(/ 10 2) ; 5
(- 6 6) ; 0
(mod 10 3) ; 1

; Variables
; Define a new variable with def
(def foo "bar") ; defines a variable called foo
; str is the concatenation macro
(str "foo " foo) ; returns "foo bar"
; the let macro allows scoped blocks
(let [foo 3 bar 9]
  foo) ; returns 3
foo ; still returns "bar"

; Expressions within expressions are confusing but necessary
(+ (* 5 5) 75) ; 100
; If statements are (if <cond> <iftrue> <iffalse>)
(if true "yes" "no") ; returns "yes"
; Equality is it's own function
(if (= 3 3) "yes" "no") ; returns "yes"
; blocks are denoted with "do"
(if (= true true)
  (do ; this do block is for when true
    (println "true") ; printline is a side effect because the return value is always the last line executed
    (str "its... " "true"))
  "false_condition"
) ; returns "its...true"

; when is another version of if (sort of)
; only doesn't allow a false route, only one predicate
(when (= 1 1)
  (do
    (println "only when true")
    (str "hello " "world!")))

; logic
(and) ; returns true
(and true) ; returns true
(and (+ 1 1)) ; returns 2
; This format always returns the first condition
(and (+ 1 1) (+ 2 2)) ; returns 2
(and true (+ 5 5)) ; returns 10
(and false (+ 5 5)) ; returns false
(let [value 20]
  (and
    (< value 56) ; is value less than 56
    (>= value 10) ; is value greater than or eq to 10
    (= value 20) ; is value equal to 20
    )) ; returns true

; or works similar, just like normal booleans
(or true) ; returns true
(or true false) ; returns true
(or false false) ; returns false
(or (+ 1 1) (+ 2 2)) ; returns 2
(or false (+ 5 5)) ; returns 10

; Cond is like the switch statement
(let [value 100]
  (cond
    (< value 25) "less than 25"
    (> value 500) "greater than 500"
    (= value 100) (do
                    (println)
                    (str "we are at " 1)) ; do in there to illustrate code complexity
    (= value "hello") "hello"
)) ; returns "we are at 1"

; Functions
; define functions with defn [param1 param2]
(defn a_func [p1 p2] (do
  (println (str p1 p2))
  (str p1 p2)
))
(a_func "hello " "world") ; retudrns "hello world"

; optional arguments are specified with the & operator, everything after the &
; is a list of arguement no matter how many are passed.
(defn optional_func [a & b]
  (println a)
  (println (first b)) ; will print second parameter passed
  (println (rest b)) ; prints the rest of the params passed
)
(optional_func 3 4 5 6) ; prints 3, 4, (5 6)

; Anonymous functions (lambda expressions)
(defn do_maths [a b f] (f a b)) ; can read like f(a, b) where f will be a function
; # means start lambda expression, % operator pulls out the arguments
(do_maths 10 20 #(<= %1 %2)) ; is 10 less than 20... returns true

; Type checking
(defn check_numbers [a b] (do
  (when (and
          (number? a) ; Check that a is a number
          (number? b)
          )(str a b)) ; if when condition is met
  ))
(check_numbers 1 "3")

;; Collections

; defines a list
(def simpleList '("list" "of" "strings")) ;a single, single quote defines a list
(first simpleList) ; returns first element
(last simpleList) ; returns last element
(rest simpleList) ; returns list but without the first element
(take 2 simpleList) ; returns first 2 elements of collection
;; we can generate a list with range
(range 10) ; returns (0 1 2 3 4 5 6 7 8 9)
; or with a lower limit
(range 5 10) ; returns (5 6 7 8 9)
; or with a step
(range 0 10 2) ; returns (0 2 4 6 8)
;; we can generate sequences with mapped elements
(iterate inc 5) ; returns infinite list from 5 with all elements incremented
(take 5 (iterate inc 5)) ; returns part of the sequence, making it useful

; defines a Vector
(def simpleVector [1 2 3 4 5 6 "seven" [8 9]]) ; note the nested vector [8 9]
(type simpleVector) ; returns clojure.lang.PersistentVector
; Vectors are immutable but we can make a new one from the old
; to append to the end use conj
(def secondVector (conj simpleVector \z)) ; defines secondVector with the z character appended to the end
secondVector ; returns [1 2 3 4 5 6 "seven" [8 9] \z]
; to append to the front use cons
(def thirdVector (cons \a secondVector)) ; a character is attached to front, note placement of parameters here
thirdVector ; returns [\a 1 2 3 4 5 6 "seven" [8 9] \z]
(type thirdVector) ; returns clojure.lang.Cons, therefore the return value isnt a vector so be careful

; Map, filters and list comprehension

; map (map is useful with vectors)
(defn doub [num_to_double] (* num_to_double 2))
(def data '(1 2 3 4 5 6 7 8 9))
(map doub data) ; takes function and data and applies function to all elements in data then returns result

; filter
(filter odd? data) ; returns a new collection but filled with all the odd numbers
; Combining filter and map...
(map doub (filter even? data)) ; returns all odd numbers in list (filter) and then doubles them (map)
(map doub (filter #(< % 5) data)) ; returns double of numbers if number is less than 5
; reduce
; reduce takes all items in a collection and reduces it to a single value
(reduce * data) ; returns 362880 by multipling all elements in data
; with map:
; the following filters all even numbers in the data, map then doubles then, reduce the adds them all together
(reduce + (map doub (filter even? data))) ; returns 40

; more filtering macros
; some returns the predicate if something in the collection matches boolean predicate or nil otherwise
(some odd? '(2 4)) ; returns nil
(some odd? [1 2 3 4]) ; returns true
(some #{2} [1 2 3 4]) ; returns 2 because 2 is in the collection

; every? returns true if every element matches the predicate
(every? odd? [1 3 5 7]) ; returns true because every element is odd
(every? #(< % 10) [1 3 5 7]) ; returns true because every value is less than 10
(every? #{10 20} [10 10 10 20 20]) ; returns true because every value is either 10 or 20
; list comprehension
; comprehensions are done with for and the syntax is new
(for [v
      (range 10)
      :while (> 4 v) ; while is a modifier
      ]
 v) ; returns (0 1 2 3) because 4 was greater than these values when they were v

; another one but with a better layout
(for [v (range 10 20)
    :when (even? v)
    ]
  v) ; returns (10 12 14 16 18) because these values are even

; last one with :let
(for [v (range 10)
      :let [y (* v 3)] ; :let allows a block of logic
      :when (odd? v)
      ]
  y)
