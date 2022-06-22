(ns storage.lesson5)

(def clothes {"Backpack" 10 "Shirt" 5})

(println (get clothes "Shirt"))

(def clothes {:backpack 10, :shirt 5})
(def clothes (assoc clothes :jeans 1))
(println clothes)

(println (update clothes :jeans inc))
(println (update clothes :jeans dec))

(println (dissoc clothes :jeans))

(def clothes {:shirt {:quantity 10, :price 100}, :backpack {:quantity 4, :price 50}})

(println clothes)

(def clothes (assoc clothes :keychain {:quantity 1, :price 20}))

(println clothes)

(println (count clothes))

; Getting non existent keys
(println (get clothes :chair {}))
(println (clothes :chair))
(println (:chair clothes))                                  ; Prevents accessing a nil DS

; UPDATE INSIDE THE DS
(println (update-in clothes [:backpack :quantity] inc))

; Accessing to values
(println (:quantity (:backpack clothes)))
; TREADING
(println (-> clothes  :backpack  :quantity))
(-> clothes  :backpack  :quantity println)