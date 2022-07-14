(ns exploring-tests.model
  [:require [schema.core :as s]])

(def empty-queue clojure.lang.PersistentQueue/EMPTY)

(defn new-hospital
  []
  {
   :g-queue empty-queue
   :lab-1   empty-queue
   :lab-2   empty-queue
   :lab-3    empty-queue
   })

(s/def PatientID s/Str)
(s/def Department [PatientID])
(s/def Hospital {s/Keyword Department})

(s/validate PatientID "William")
;(s/validate PatientID 15)

(s/validate Department ["Willliam" "Daniela"])

(s/validate Hospital {:g-queue ["William" "Daniela"]})