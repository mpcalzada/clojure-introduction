(ns mutability-with-atoms.model)

(def empty-queue clojure.lang.PersistentQueue/EMPTY)

(defn new-hospital
  []
  {
   :g-queue empty-queue
   :lab-1   empty-queue
   :lab-2   empty-queue
   :lab-3    empty-queue
   })