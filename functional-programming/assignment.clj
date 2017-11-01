; Koans

;1

(= 2 2/1 (- 2 0))

(not= :fill-in-the-blank "literally anything")

(= '(:a :b :c :d :e) (cons :a '(:b :c :d :e)))

(= :a (peek '(:a :b :c :d :e)))

(= [1 2] (vec '(1 2)))

(= (list 1 2 3) (vector 1 2 3))

(= #{2 3} (clojure.set/intersection #{1 2 3 4} #{2 3 5}))

(= 1 ({:a 1 :b 2} :a))

(= {1 "January" 2 "February"} (assoc {1 "January" } 2 "February"))

(defn multiply-by-ten [n]
  (* 10 n))
(= 20 (multiply-by-ten 2))

(= 25 (__ (fn [n] (* n n))))

(defn square [x] (* x x))
(= 25 (let [inc-and-square (comp square inc)]
    (inc-and-square 4)))

(= '(1 2 3 4) (range 1 5))

(= (range 0 20) (take 20 (iterate inc 0)))

(= '(0 1 2 3 4 5)
  (for [index (range 6)]
    index))

(= '(0 1 4 9 16 25)
  (map (fn [index] (* index index))
    (range 6))
  (for [index (range 6)]
    (* index index)))

(defn is-even? [n]
  (if (= n 0)
    true
    (not (is-even? (dec n)))))
(= true (is-even? 0))

(defn is-even-bigint? [n]
  (loop [n n
    acc true]
  (if (= n 0)
    __
  (recur (dec n) (not acc)))))
(= false (is-even-bigint? 100003N))

(= (str "First comes love, "
  "then comes marriage, "
  "then comes Clojure with the baby carriage")
((fn [[a b c]] __)
  ["love" "marriage" "Clojure"]))

(def test-address
  {:street-address "123 Test Lane"
  :city "Testerville"
  :state "TX"})
(= "123 Test Lane, Testerville, TX"
  (let [{:keys [street-address __ __]} test-address]
  __))
