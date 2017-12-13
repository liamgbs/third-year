;;; Liam Gibbings
;;; B4044389
;;; Task one

;;; Basic Koans

;; 1. Some things may appear different, but be the same

; 2 and 2/1, although they look different are both of type Long. (long 2) evalulates
; to the same as these, as does (- 2 0) since 2-0=2 which is also a long.
(= 2 2/1 (long 2) (- 2 0))

;; 2. When things cannot be equal, they must be different

; not= will return true if all expressions are not equal. "clojure" is indeed not
; equal to :fill-in-the-blank so the whole thing evalulates to true.
(not= :fill-in-the-blank "clojure")

;; 3. Construction of lists by adding an element to the front is simple

; cons returns a new sequence with the first argument appended to the FRONT of the
; second sequence argument. This means that (cons :a '(:b :c :d :e)) returns the lazy sequence
; '(:b :c :d :e) with :a appended to the front which gives a new (since the sequence is immutable)
; lazy sequence of '(:a :b :c :d :e)
(= '(:a :b :c :d :e) (cons :a '(:b :c :d :e)))

;; 4. You can use a list like a stack to get the first element

; peek returns the first element in the
(= :a (peek '(:a :b :c :d :e)))

;; 5. You can populate vectors with any number of elements at once

; vec converts the lazy sequence '(1 2) to a vector which can be defined using square brackets []
; so (vec '(1 2) is the same as [1 2]
(= [1 2] (vec '(1 2)))

;; 6. Equality with collections is in terms of values

; (list 1 2 3) returns a persistent list and (vector 1 2 3) returns a persistent vector.
; Because they are both collections containing the same elements, clojure treats them as equal.
(= (list 1 2 3) (vector 1 2 3))

;; 7. Clojure can tell you the intersection of sets

; the syntax #{<elements>} is short hand for defining a set. The intersection of two sets is
; a set which holds the values which are present in BOTH sets. Therefore #{2 3} is the same as
; the intersection of #{1 2 3 4} and #{2 3 5} since 2 and 3 are present in both sets.
(= #{2 3} (clojure.set/intersection #{1 2 3 4} #{2 3 5}))

;; 8. Maps can be used as lookup functions

; Inside {:a 1 :b 2}, the key :a is is associated with the value 1.  using ({:a 1 :b 2} :a),
; we are looking up the value assigned to :a... which is 1. Therefore 1 is equal to ({:a 1 :b 2} :a).
(= 1 ({:a 1 :b 2} :a))

;; 9. Maps are immutable, but you can create a new, 'changed' version

; The assoc function will take the {1 "January" } map, which is immutable so rather than
; modify it and add the new key-val pair 2 "February", it will construct a brand new
; map containing all the key-val pairs it now knows about and return it.
(= {1 "January" 2 "February"} (assoc {1 "January" } 2 "February"))

;; 10. Functions are often defined before they are used

; defn multiply-by-ten defines a function with that name, it takes one parameter called n.
; the one line (* 10 n)) simply means multiply the parameter n by 10. Therefore (multiply-by-ten 2)
; will return 20
(defn multiply-by-ten [n]
  (* 10 n))
(= 20 (multiply-by-ten 2))

;; 11. Higher-order functions take function arguments

; The first anonymous function is evaluated with 5 as the argment, this simply
; returns the argument. That return value is then passed to the next anonymous
; function which squares its input and returns it. In this case the input is 5
; and 5 squared is 25 so the whole thing returns true.
(= 25 (
  (fn [n] (n 5))
  (fn [n] (* n n))))

;; 12. Functions can join forces as one 'composed' function

;
(defn square [x] (* x x))
(= 25 (let [inc-and-square (comp square inc)]
    (inc-and-square 4)))

;; 13. There is a wide range of ways to generate a sequence

; The range function generates a sequence (specifically a LongRange)
; from x to y-1 where x and y are the parameters. Because of this, (range 1 5)
; is equal to the lazy sequence (1 2 3 4).
(= '(1 2 3 4) (range 1 5))

;; 14. Iteration provides an infinite lazy sequence

; (iterate inc 0) takes an initial argument, in this case zero, and applies a
; macro, in this case inc, to create an infinite sequence. In other words, (iterate inc 0)
; will generate a sequence from 0 to infinity. To extract a usable subset of the
; infinite sequence we can use 'take 20' which will extract the first 20 elements from
; that infinite sequence. So (take 20 (iterate inc 0)) will give us 0 - 19 which are
; the first 20 elements, this is also the same as saying (range 0 20).
(= (range 0 20) (take 20 (iterate inc 0)))

;; 15. Sequence comprehensions can bind each element in turn to a symbol

;
(= '(0 1 2 3 4 5)
  (for [index (range 6)]
    index))

;; 16. Sequence comprehensions can easily emulate mapping16. Sequence comprehensions can easily emulate mapping

;
(= '(0 1 4 9 16 25)
  (map (fn [index] (* index index))
    (range 6))
  (for [index (range 6)]
    (* index index)))

;; 17. Recursion ends with a base case. Explain the following code (you do not need to add to it).

; something about tail recursion
(defn is-even? [n]
  (if (= n 0)
    true
    (not (is-even? (dec n)))))
(= true (is-even? 0))

;; 18. Having too many stack frames requires explicit tail calls with recur.

;
(defn is-even-bigint? [n]
  (loop [n n
    acc true]
  (if (= n 0)
    __
  (recur (dec n) (not acc)))))
(= false (is-even-bigint? 100003N))

;; 19. Destructuring is an arbiter: it breaks up arguments

;
(= (str "First comes love, "
  "then comes marriage, "
  "then comes Clojure with the baby carriage")
((fn [[a b c]] __)
  ["love" "marriage" "Clojure"]))


;; 20. Break up maps succinctly

;
(def test-address
  {:street-address "123 Test Lane"
  :city "Testerville"
  :state "TX"})
(= "123 Test Lane, Testerville, TX"
  (let [{:keys [street-address __ __]} test-address]
  __))


;;; Advanced Koans

;; 1. Squaring Lists
; The lone function accepts a single parameter called numbs and checks that it is a sequence of some kind
; the filter then filters out any results that arent clojure number types using the number? predicate
; map then maps the (guaranteed to be) number elements to the anonymous function: #(* % %) which squares each element
; this mapped sequence is then returned
(defn square [numbs] (when (sequential? numbs)
  (->>
    numbs
    (filter number? numbs)
    (map #(* % %))
  )))

;; 2. Encoding
; the codes variable defines a map with a-z as keys with corresponding values
; being their respective morse code representation.
(def codes {\a ".-" \b "-..." \c "-.-." \d "-.." \e "."
               \f "..-." \g "--." \h "...." \i ".." \j ".---"
               \k "-.-" \l ".-.." \m "--" \n "-." \o "---"
               \p ".--." \q "--.-" \r ".-." \s "..." \t "-"
               \u "..-" \v "...-" \w ".--" \x "-..-" \y "-.--" \z "--.."})

; The ascii-morse function takes in a string and converts it to a vector. Using the above map,
; the vector of characters is converted to morse code and then passed back to the
; apply function to convert back to a string representation.
(defn ascii-morse [to-convert]
  (->>
    (vec to-convert)
    (map #(get codes %))
    (apply str)
))

(defn morse-ascii [to-convert] (when (sequential? to-convert)
  (->>
    to-convert
    (map #(get (zipmap (vals codes) (keys codes)) %))
    (apply str)
)))

(ascii-morse "hello")
(morse-ascii ["...." "." ".-.." ".-.."])

;; 3. Meteor Falls

(slurp "https://data.nasa.gov/resource/y77d-th95.json")
