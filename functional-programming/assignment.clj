;;; Liam Gibbings
;;; B4044389
;;; Task one

in
;; 3. Meteor Falls

(slurp "https://data.nasa.gov/resource/y77d-th95.json")

(:require [clojure.data.json :as json])

(json/read)
