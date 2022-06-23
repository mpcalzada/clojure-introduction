(ns store.lesson2)

(def names ["daniel" "william" "carl" "paul" "lucy" "ana"])

(defn my-counting
  ([sequence] (my-counting 0 sequence))
  ([total-so-far sequence]
   (if (seq sequence)
     (recur (inc total-so-far) (next sequence))
     total-so-far)))

;(println (my-counting names))
;(println (my-counting []))

(defn my-counting-2
  [elements]
  (loop
    [total-so-far 0
     remaining-elements elements]

    (if (seq remaining-elements)
      (recur (inc total-so-far) (next remaining-elements))
      total-so-far)))

(println (my-counting-2 names))

(defn my-counting-3
  [elements]
  ((let [total-so-far 0]
     (for [_ elements]
        (inc total-so-far))
     total-so-far)))

(println (my-counting-3 names))