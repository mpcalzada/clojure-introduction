(ns store.core)

;list
["daniel" "william" "carl" "paul" "lucy" "ana"]

;map
{"william" 37, "paul" 39}

;linked list
'(1 2 3 4 5)

;set
#{"daniel" "william"}


(def names ["daniel" "william" "carl" "paul" "lucy" "ana"])
(map println names)

(defn private-map
  [func collection]
  ((for [col collection]
    (func col)))
  )

;(private-map println names)

(defn my-map
  [function sequence]
  (let [first-element (first sequence)]
    (function first-element)
    (my-map function (rest sequence))))

(my-map println names)