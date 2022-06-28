(ns record-protocol-multimethod.lesson4
  (:use [clojure.pprint])
  (:require [record-protocol-multimethod.logic :as r.logic]))

(defrecord PrivatePatient [id name birthdate situation])
(defrecord InsuredPatient [id name birthdate health-insurance situation])

(defn not-urgent?
  [patient]
  (not= :urgent (:situation patient :regular)))

(defprotocol Authorizable
  (needs-authorization? [patient exam cost]))

(extend-type PrivatePatient
  Authorizable
  (needs-authorization? [patient _ cost]
    (and (>= cost 50) (not-urgent? patient))))

(extend-type InsuredPatient
  Authorizable
  (needs-authorization? [patient exam _]
    (let [health-insurance (get patient :health-insurance)]
      (and (not (some #(= %1 exam) health-insurance)) (not-urgent? patient)))))

(defn authorizer-type
  [request]
  (let [patient (:patient request)
        situation (:situation patient)
        urgent? (= :urgent situation)]
    (if urgent?
      :always-authorize
      (class patient))))

; SIMPLIFYING IMPLEMENTATION BY SEGREGATING RESPONSIBILITIES

(defmulti needs-authorization-for-request? authorizer-type)

(defmethod needs-authorization-for-request?
  :always-authorize
  [request]
  false)

(defmethod needs-authorization-for-request?
  PrivatePatient
  [request]
  (>= (:cost request) 50))

(defmethod needs-authorization-for-request?
  InsuredPatient
  [request]
  (not (some #(= %1 (:exam request)) (:health-insurance (:patient request)))))

(defmulti needs-authorization-multi? class)

(defmethod needs-authorization-multi?
  PrivatePatient
  [patient]
  (println "Invoking for private patient")
  true)

(defmethod needs-authorization-multi?
  InsuredPatient
  [patient]
  (println "Invoking for insured patient")
  false)

(let [private-patient (->PrivatePatient 15 "William" "09/18/1981" :regular)
      insured-patient (->InsuredPatient 15 "William" "09/18/1981" [:x-ray :ultrasound] :regular)]
  ;(pprint (needs-authorization-multi? private-patient))
  ;(pprint (needs-authorization-multi? insured-patient))
  (pprint (needs-authorization-for-request? {:patient private-patient, :cost 40, :exam :blood-test}))
  (pprint (needs-authorization-for-request? {:patient insured-patient, :cost 1000, :exam :ultrasound}))
  )