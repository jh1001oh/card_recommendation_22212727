1. Conceptualization


(맞춤형 신용카드 혜택 최적화 계산기)

(Logo) - option

(22212727, 오지후, nyeong2216@daum.net)



[ Revision history ]


Revision date 22/03/2026
Version # 1.0
Description 초안
Author




= Contents =


1. Business purpose ..................................................................................

2. System context diagram .......................................................................

3. Use case list .........................................................................................

4. Concept of operation ............................................................................ 

5. Problem statement ................................................................................

6. Glossary .................................................................................................

7. References .................................................................................................


1. Business purpose
1.1 Project background
   
![](https://github.com/jh1001oh/conceptualization--22212727-/blob/main/%EC%8B%A0%EC%9A%A9%EC%B9%B4%EB%93%9C%20%EC%9D%B4%EC%9A%A9%EC%8B%A4%EC%A0%81.png?raw=true)

 현대사회에서 신용카드 이용 실적은 꾸준히 증가하고 있다. 소비자들은 이제 결제 수단을 넘어 다양한 혜택과 맞춤형 서비스까지 고려하며 카드를 선택하고 있다.
특히 온라인 쇼핑, 배달 서비스, ott가 일상화되며 카드 사용액은 생활 전반에 걸쳐 확대되고 있다. 이에 발맞춰 카드사들은 고객의 소비 데이터를 분석해 맞춤화된 혜택을 제공하려는 경쟁이 심화되고 있다.
하지만 수많은 카드가 경쟁적으로 나올수록 소비자는 자신의 소비 성향에 핏하는 카드를 찾는 게 어려워지고 있다. 나의 경우에도 나에게 핏하는 카드를 찾기 위해 며칠간 비교했지만 결국엔 찾지 못 한 경험이 있다. 
이처럼 카드사별로 제고하는 할인이나 적립 혜택이 각기 다르기에 사용자가 직접 비교하기엔 많은 시간과 노력이 필요하다. 예를 들어 한 카드는 외식이나 배달에 큰 할인을 제공하지만 온라인 쇼핑에는 혜택이 미미할 수도 있고 다른 카드는 주유시 적립률이 높지만 ott에는 혜택이 없을 수가 있다.
 이러한 어려움을 해결하고자 사용자의 월 평균 지출 패턴을 기반으로 맞춤형 신용카드를 추천하는 어플리케이션을 개발하는 것을 목표로 한다. 사용자가 주요한 카테고리 별로 자신의 월 평균 지출액을 입력하면 다양한 카드의 혜택을 자동으로 분석하여 사용자가 가장 높은 할인 및 정립을 받을 수 있는 카드를 추천해서 제공한다.
단순한 카드 정보 제공에서 나아가 사용자가 실질적으로 경제적인 이익을 극대화할 수 있도록 맞춤형 해결방안을 제공하는 것이다. 또한 사용자가 본인의 지출액을 객관적으로 파악하게 함으로써 합리적인 소비 습관 형성을 도울 수 있다.

1.2 Goal

신용카드를 추천해주는 어플리케이션 개발
사용자가 카테고리별로 월평균 지출액을 입력하면 이에 맞는 신용카드 혜택 계산
가장 높은 할인과 적립을 받을 수 있도록 추천

1.3 Target market

자신의 소비 습관에 맞는 카드를 추천받고 싶은 소비자

2. System context diagram

- Build a diagram to show the relationships between “System” and “Users”.
- Context models are used to illustrate the operational context of a system. They show what lies outside the system boundaries.
- Make your description for the terms in the diagram.
- 12pt, 160%.






3. Use case list

- Find use cases in your project.
- Make your short description for each use case (table type).
- 12pt, 160%.




ex)
1) Login

Actor
Customer, Manager
Description
고객과 매니저가 각자 자신의 아이디로 로그인한다.

4. Concept of operation

- Describe how to operate the use cases (table type).
- 12pt, 160%.


ex)
1) Login

Purpose
앱을 사용하기 위해 등록된 사용자인지 확인
Approach
사용자가 앱을 실행 후 로그인 시, ID, PW를 입력 후 로그인을 요청하면 서버에서 회원 정보를 조회 후 로그인 성공/실패 여부 확인한다.
Dynamics
앱 실행 시 로그인할 경우
Goals
로그인 기능을 구현한다.






5. Problem statement

- Describe the problems the project should be considered (including technical difficulties).
- Describe the Non-Functional Requirements (NFRs).
- 12pt, 160%.











6. Glossary

- Specifically describe all of the terms used in this documents.
- 12pt, 160%.








7. References

- Describe all of your references (book, paper, technical report etc).
- 12pt, 160%.

신용카드 이용실적: https://www.index.go.kr/unity/potal/main/EachDtlPageDetail.do?idx_cd=2433

