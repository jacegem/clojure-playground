(ns living-clojure.chap-1.2-클로저-데이터)

'(1 2 "jam" :marmalade-jar)
;=> (1 2 "jam" :marmalade-jar)

'(1, 2, "jam", :bee)
;=> (1 2 "jam" :bee)

(first '(:rabbit :pocket-watch :marmalade :door))
;=> rabbit

(rest '(:rabbit :pocket-watch :marmalade :door))
;=> (:pocket-watch :marmalade :door)

(first
 (rest '(:rabbit :pocket-watch :marmalade :door)))
;=> :pocket-watch

(first
 (rest (rest (rest '(:rabbit :pocket-watch :marmalade :door)))))
;=> :door

(first
 (rest (rest (rest (rest (rest '(:rabbit :pocket-watch :marmalade :door)))))))
;=> nil

(cons 5 '())
;=> (5)

;; nil로 리스트 만들기
(cons 5 nil)
;=> (5)

(cons 3 (cons 4 (cons 5 nil)))
;=> (3 4 5)

(cons 2
      (cons 3 (cons 4 (cons 5 nil))))
;=> (2 3 4 5)

'(1 2 3 4 5)
;=> (1 2 3 4 5)

(list 1 2 3 4 5)
;=> (1 2 3 4 5)

;; 벡터를 사용해 인덱스로 데이터 접근하기
[:jar1 1 2 3 :jar2]
;=> [:jar1 1 2 3 :jar2]

(first [:jar1 1 2 3 :jar2])
;=> :jar1

(rest [:jar1 1 2 3 :jar2])
;=> (1 2 3 :jar2)

(nth [:jar1 1 2 3 :jar2] 0)
;=> :jar1

(nth [:jar1 1 2 3 :jar2] 2)
;=> 2

;; 벡터에 last 적용
(last [:rabbit :pocket-watch :marmalade])
;=> :marmalade

;; 리스트에 last 적용
(last '(:rabbit :pocket-watch :marmalade))
;=> :marmalade

;; 벡터의 인덱스 접근 성능이 더 좋다.

;; 컬렉션들의 공통점들
(count [1 2 3 4])
;=> 4

;; conj는 벡터의 맨 뒤에 요소를 추가한다.
(conj [:toast :butter] :jam)
;=> [:toast :butter :jam]

;; 여러 개의 요소를 맨 뒤에 추가한다.
(conj [:toast :butter] :jam :honey)  

;; conj는 리스트의 맨 앞에 요소를 추가한다.
(conj '(:toast :butter) :jam)
;=> (:jam :toast :butter)

;; 여러 개의 요소를 맨 앞에 추가한다.
(conj '(:toast :butter) :jam :honey)
;=> (:honey :jam :toast :butter)

;; 맵을 사용해 키-값 쌍의 데이터를 저장
{:jam1 "strawberry" :jam2 "blackberry"}
;=> {:jam1 "strawberry", :jam2 "blackberry"}

{:jam1 "strawberry", :jam2 "blackberry"}
;=> {:jam1 "strawberry", :jam2 "blackberry"}

;; get을 명시적으로 사용한 예
(get {:jam1 "strawberry", :jam2 "blackberry"} :jam2)
;=> "blackberry"

(get {:jam1 "strawberry", :jam2 "blackberry"} :jam3 "not found")
;=> "not found"

;; 키를 함수로 사용해 값 가져오기
(:jam2 {:jam1 "strawberry", :jam2 "blackberry" :jam3 "marmalade"}) 
;=> "blackberry"

;; keys 함수
(keys {:jam1 "strawberry", :jam2 "blackberry" :jam3 "marmalade"})
;=> (:jam1 :jam2 :jam3)

;; vals 함수
(vals {:jam1 "strawberry", :jam2 "blackberry" :jam3 "marmalade"})
;=> ("strawberry" "blackberry" "marmalade")

(assoc {:jam1 "strawberry", :jam2 "blackberry"} :jam1 "orange")
;=> {:jam1 "orange", :jam2 "blackberry"}

(dissoc {:jam1 "strawberry", :jam2 "blackberry"} :jam1)  
;=> {:jam2 "blackberry"}

(merge 
      {:jam1 "red" , :jam2 "black"}
      {:jam1 "orange", :jam3 "red"}
      {:jam4 "blue"}
 )
;=> {:jam1 "orange", :jam2 "black", :jam3 "red", :jam4 "blue"}

;; 집합을 사용해 유일한 데이터의 컬렉션 표현하기
#{:red :blue :white :pink}
;=> #{:white :red :blue :pink}

;; 집합을 생성할 때 중복은 허용되지 않는다.
#{:red :blue :white :pink :pink}
; Syntax error reading source at (REPL:136:33).
; Duplicate key: :pink

(clojure.set/union #{:r :b :w} #{:w :p :y})
;=> #{:y :r :w :b :p}







