# 실수를 엮어보자, 실타래 🧶

## 프로젝트 소개

`실타래`는 실수를 긍정적인 경험으로 전환시키고자 하는 아이디어에서 출발했습니다. 프로젝트의 목표는 사람들이 실수를 쉽게 공유하고, 이를 통해 성장할 수 있는 커뮤니티를 조성하는 것입니다. 실타래의 공유 기능을 통해, 모두가 비슷한 실수를 하며 배운다는 사실을 공감하고 실수를 웃어넘기며 서로를 격려할 수 있도록 만들고자 했습니다.

<br/>

## 기능소개
- **실수 작성 기능**: 새로운 실수를 기록하고 공유하는 기능입니다. 작성한 실수는 나의 실수 페이지에서 확인 가능합니다.
- **좋아요 및 댓글 기능**: 실수에 좋아요를 누르거나, 댓글을 달 수 있습니다.
- **실수 관련 태그 기능**: 실수에 관련된 태그를 추가하여 관련실수를 분류 및 필터링 할 수 있습니다.
- **인기순/최신순 정렬 피드**: 인기 있는 실수 또는 최신 실수들을 피드 형태로 확인할 수 있습니다.
- **기타 회원 관련 기능**: 구글 계정을 이용하여 가입할 수 있으며, 프로필 사진 및 닉네임을 변경할 수 있습니다.

<br/>

## 기술스택

### 백엔드

|                                        Spring Boot 3.2.0                                         |                                         JAVA 17                                         |                                                                  MySQL                                                                   |                                                                  redis                                                                   |
| :----------------------------------------------------------------------------------------------: | :-------------------------------------------------------------------------------------: |:----------------------------------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------------------------------:|
| <img src="https://github.com/coding-union-kr/siltarae-be/assets/67768976/50f457d7-c158-4442-9659-669a7ee931a2" width="100" height="100"> | <img src="https://github.com/coding-union-kr/siltarae-be/assets/67768976/856a32af-f7e0-48e2-826c-49a5bdd08ea1" width="100" height="100"> | <img src="https://github.com/coding-union-kr/siltarae-be/assets/67768976/9e9907e1-1e9c-439c-8251-313047aaac5d" width="100" height="100"> | <img src="https://github.com/coding-union-kr/siltarae-be/assets/67768976/ffc77f23-81a0-4d04-9a19-7b6065ad799b" width="100" height="100"> |

|                                         Spring Data JPA                                          |                                                         QueryDsl                                                         |                                             Docker                                              |                                          AWS EC2                                           |
| :----------------------------------------------------------------------------------------------: | :----------------------------------------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------: | :----------------------------------------------------------------------------------------: |
| <img src="https://github.com/coding-union-kr/siltarae-be/assets/67768976/9a0cdd8c-5cd0-44b6-9cb7-8d571f11c2ca" width="100" height="100"> | <img src="https://github.com/coding-union-kr/siltarae-be/assets/67768976/571d3574-e97e-42b4-a03c-1ab6c586542d" width="100" height="100"> | <img src="https://github.com/coding-union-kr/siltarae-be/assets/67768976/4285f8f1-0d8e-497b-bd53-ddead476b699" width="100" height="100"> | <img src="https://github.com/coding-union-kr/siltarae-be/assets/67768976/d8935819-fecf-45ec-9d41-61d4e54d3fac" width="100" height="100"> |

|                                                                  NGINX                                                                   |
|:----------------------------------------------------------------------------------------------------------------------------------------:|
| <img src="https://github.com/coding-union-kr/siltarae-be/assets/67768976/09db4913-e11b-44c6-8994-2392f70a4a22" width="100" height="100"> |

<br/>

### 프론트

|                                        Yarn Berry                                        |                                          React                                           |                                        TypeScript                                        |                                                          TanStack Query                                                           |
| :--------------------------------------------------------------------------------------: | :--------------------------------------------------------------------------------------: | :--------------------------------------------------------------------------------------: | :-------------------------------------------------------------------------------------------------------------------------------: |
| <img src="https://github.com/coding-union-kr/siltarae-be/assets/67768976/4bf3b758-a688-48fd-a9f3-43f03fe10de2" width="100" height="100"> | <img src="https://github.com/coding-union-kr/siltarae-be/assets/67768976/b491799f-5ea5-4b64-ad9a-69d155cf23ff" width="100" height="100"> | <img src="https://github.com/coding-union-kr/siltarae-be/assets/67768976/131ec2ed-1487-4baf-aa93-f9ed8031ed78" width="100" height="100"> | <img src="https://github.com/coding-union-kr/siltarae-be/assets/67768976/493afbfc-8f33-4f99-9819-5226d1d965c5" width="100" height="100"> |

|                                                   Axios                                                    |                                                             Tailwind                                                             |                               DaisyUI                                |                                                                  Framer                                                                  |
| :--------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------: |:----------------------------------------------------------------------------------------------------------------------------------------:|
| <img src="https://github.com/coding-union-kr/siltarae-be/assets/67768976/f5c0351d-3c92-45f3-a497-1a91a008e6c3" width="100" height="100"> | <img src="https://github.com/coding-union-kr/siltarae-be/assets/67768976/9935a154-e1cb-4e0d-b83c-a91eb0a538d0" width="100" height="100"> | <img src="https://github.com/coding-union-kr/siltarae-be/assets/67768976/16f22875-9ba1-48e7-a6b6-cb6ca4458d6d" width="100" height="100"> | <img src="https://github.com/coding-union-kr/siltarae-be/assets/67768976/70c74376-a291-451d-9b67-607bea22e952" width="100" height="100"> |


<br/>

## 서비스 아키텍처

<br/>

## 팀명

**위버즈 (Weavers)**

저희의 팀명 `위버즈`는 `Weavers`, 즉 '실타래를 엮는 사람들'이라는 단어에서 영감을 받아 탄생했습니다. 저희 팀은 실수와 도전을 하나의 타래로 엮어 나가며, 이를 통해 실수를 부정적으로만 보지 않고 그 안에서 배움을 찾고 성장하는 커뮤니티를 만들어 낼려고 노력했습니다.

<br/>

## 팀원소개

|                                                                    파덕                                                                    |                                                                    매니                                                                    |                                                                    람쥐                                                                    |                                                                    서비                                                                    |
|:----------------------------------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------------------------------:|
| <img src="https://github.com/coding-union-kr/siltarae-be/assets/67768976/8ecfd628-eefe-4d01-8c0d-6e1327ff0596" width="100" height="100"> | <img src="https://github.com/coding-union-kr/siltarae-be/assets/67768976/ad4ef88b-bbe5-4906-bd6f-7e971f206194" width="100" height="100"> | <img src="https://github.com/coding-union-kr/siltarae-be/assets/67768976/f43af542-9e43-4289-abfe-7e65acc5063b" width="100" height="100"> | <img src="https://github.com/coding-union-kr/siltarae-be/assets/67768976/1e226d23-86b9-4c76-a240-8ae974504aaf" width="100" height="100"> |
|                                                                 백엔드, 팀장                                                                  |                                                                 백엔드, 팀원                                                                  |                                                                 프론트, 팀원                                                                  |                                                                 프론트, 팀원                                                                  |
|                                         태그 관련 API 구현 <br/> 회원 관련 API 구현 <br/> 소셜 로그인 및 토큰 인증 구현                                          |                                                프로젝트 배포 <br/> API 구현 <br/> 이메일 암호화, 복호화 구현                                                |                                                컴포넌트 API 연결 <br/> 소셜 로그인 구현 <br/> 무한스크롤 구현                                                |                                 실수 피드 상세 페이지 및 컴포넌트 구현 <br/> 실수 등록 모달 컴포넌트 <br/> 개인 실수 관련 페이지 및 컴포넌트 구현                                  |

<br/>
