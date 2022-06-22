(ns store.lesson1)

(def names ["daniel" false "william" "carl" "paul" "lucy" "ana"])

(defn my-map
  [function sequence]
  (let [first-element (first sequence)]
    (if (not (nil? first-element))
      (do (function first-element)
          (my-map function (rest sequence))))))

;(my-map println names)

(defn my-map
  [function sequence]
  (let [first-element (first sequence)]
    (if (not (nil? first-element))
      (do (function first-element)
          (recur function (rest sequence))))))

(my-map println (range 10000000))
