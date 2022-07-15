(ns exploring-tests.logic-test
  (:require [clojure.test :refer :all]
            [exploring-tests.logic :refer [fits-in-queue?, arrived-at, transfer]]
            [exploring-tests.model :as h.model]
            [schema.core :as s]))

(s/set-fn-validation! true)

(deftest fits-in-queue?-test
  ;zero boundary
  (testing "That it fits in the queue"
    (is (fits-in-queue? {:g-queue []} :g-queue)))

  ;limit boundary
  (testing "That it doesn't fit a new patient when the queue is full"
    (is (not (fits-in-queue? {:q-queue [1 2 3 4 5]} :q-queue))))

  ;above limit boundary
  (testing "That it doesn't fit a new patient when the queue is above the limit"
    (is (not (fits-in-queue? {:q-queue [1 2 3 4 5 6]} :q-queue))))

  ;below limit boundary
  (testing "That it doesn't fit a new patient when the queue is below the limit"
    (is (fits-in-queue? {:q-queue [1 2 3 4]} :q-queue))
    (is (fits-in-queue? {:q-queue [1 2]} :q-queue)))

  (testing "That is doesn't fit in the queue when the department doesn't exists"
    (is (not (fits-in-queue? {:g-queue []} :x-ray))))
  )

(deftest arrived-at-test
  (let [full-hospital {:g-queue [1 58 96 74 32]}]
    (testing "That the new patient will be added to the department if the queue is not full"
      (is (= {:g-queue [1 2 3 4 5]}
             (arrived-at {:g-queue [1 2 3 4]}, :g-queue, 5)))

      (is (= {:g-queue [1 2 5]}
             (arrived-at {:g-queue [1 2]}, :g-queue, 5)))

      #_(is (= {:hospital {:g-queue [1 2 3 4 5]}, :result :success}
               (arrived-at {:g-queue [1 2 3 4]}, :g-queue, 5)))

      #_(is (= {:hospital {:g-queue [1 2 5]}, :result :success}
               (arrived-at {:g-queue [1 2]}, :g-queue, 5)))

      )
    (testing "That wont add the new patient to department if the queue is full"
      ; Untyped exception is not a good practice
      (is (thrown? clojure.lang.ExceptionInfo (arrived-at {:g-queue [1 58 96 74 32]}, :g-queue, 96)))

      ;(is (try
      ;       (arrived-at {:g-queue [1 58 96 74 32]}, :g-queue, 96)
      ;       false
      ;       (catch Exception e
      ;         (= ::impossible-to-add-patient-to-the-queue (:type (ex-data e))))))

      #_(is (= {:hospital full-hospital, :result :impossible-to-add-patient-to-the-queue}
               (arrived-at full-hospital, :g-queue, 96)))

      ))
  )

(deftest transfer-test
  (testing "that transfer works if patient will fit in the destination department"
    (let [original-hospital {:g-queue (conj h.model/empty-queue "5") :x-ray (conj h.model/empty-queue)}]
      (is (= {:g-queue [] :x-ray ["5"]} (transfer original-hospital :g-queue :x-ray))))

    (let [original-hospital {:g-queue (conj h.model/empty-queue "51" "5")
                             :x-ray   (conj h.model/empty-queue "13")}]
      (is (= {:g-queue ["5"] :x-ray ["13" "51"]} (transfer original-hospital :g-queue :x-ray))))
    )

  (testing "that transfer doesn't works if patient will fit in the destination department"
    (let [full-hospital {:g-queue (conj h.model/empty-queue "5") :x-ray (conj h.model/empty-queue "1" "23" "45" "67" "42")}]
      (is (thrown? clojure.lang.ExceptionInfo (transfer full-hospital :g-queue :x-ray)))))

  (testing "cannot invoke transfer without a hospital"
    (is (thrown? clojure.lang.ExceptionInfo (transfer nil :g-queue :x-ray))))

  (testing "mandatory conditions"
    (let [hospital {:g-queue (conj h.model/empty-queue "5")
                    :x-ray (conj h.model/empty-queue "1" "23" "45" "67")}]
      (is (thrown? AssertionError
                   (transfer hospital :random-dep :x-ray)))

      (is (thrown? AssertionError
                   (transfer hospital :x-ray :random-dep))))))