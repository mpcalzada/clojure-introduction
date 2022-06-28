(ns record-protocol-multimethod.lesson2
  (:use [clojure.pprint])
  (:import (java.util Calendar)))

(defrecord Patient [id name birthdate])

(defrecord PrivatePatient [id name birthdate])

(defrecord InsuredPatient [id name birthdate health-insurance])

;(defn needs-authorization?
;  [patient exam cost]
;  (if (= PrivatePatient (type patient))
;    (>= cost 50))
;  (if (= InsuredPatient (type patient))
;    (let [health-insurance (get patient :health-insurance)]
;      (not (some #(= %1 exam) health-insurance)))))

(defprotocol Authorizable
  (needs-authorization? [patient exam cost]))

(extend-type PrivatePatient
  Authorizable
  (needs-authorization? [_ _ cost]
    (>= cost 50)))

(extend-type InsuredPatient
  Authorizable
  (needs-authorization? [patient exam _]
    (let [health-insurance (get patient :health-insurance)]
    (not (some #(= %1 exam) health-insurance)))))

(let [private-patient (->PrivatePatient 15 "William" "09/18/1981")
      insured-patient (->InsuredPatient 15 "William" "09/18/1981" [:x-ray :ultrasound])]
  (pprint (needs-authorization? private-patient, :x-ray, 500))
  (pprint (needs-authorization? private-patient, :x-ray, 49))
  (pprint (needs-authorization? insured-patient, :x-ray, 12123132))
  (pprint (needs-authorization? insured-patient, :dental, 12123132))
  )


(defrecord InsuredPatient [id name birthdate health-insurance]
  Authorizable
  )


(defprotocol Dateable
  (to-ms [this]))

(extend-type java.lang.Number
  Dateable
  (to-ms [this] this))

(extend-type java.util.Date
  Dateable
  (to-ms [this] (.getTime this)))

(extend-type Calendar
  Dateable
  (to-ms [this] (to-ms (.getTime this))))

(pprint (to-ms 56))
(pprint (to-ms (java.util.Date.)))
(pprint (to-ms (java.util.GregorianCalendar.)))
(pprint (to-ms "56"))
