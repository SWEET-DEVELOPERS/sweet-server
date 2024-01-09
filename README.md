<div align="center">
<H1>🍭 스윗한 마음이 모여 하나의 선물이 되다, <br/>선물 준비 서비스 SWEET</H1>
</div>

> 배포 링크 <br />
> **https://sweetgift.vercel.app/**

<div align="center">
      <a href="https://hits.seeyoufarm.com"><img src="https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FSWEET-DEVELOPERS%2Fsweet-server&count_bg=%23FF2176&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false"/></a>
      <img src="https://img.shields.io/github/issues-raw/SWEET-DEVELOPERS/sweet-server?color=2379C83D">
      <img src="https://img.shields.io/github/issues-closed-raw/SWEET-DEVELOPERS/sweet-server?color=176842">
</div>

## 📄 목차

- [📄 목차](#-목차)
- [✍🏻 프로젝트 개요](#-프로젝트-개요)
- [🥰 팀원 소개](#-팀원-소개)
- [🍭 핵심 기능](#-핵심-기능)
  - [소셜 로그인 기능](#-소셜-로그인-기능)
  - [선물 준비 모임 개설 기능](#-선물-준비-모임-개설-기능)
  - [선물 준비 모임 공유 기능](#-선물-준비-모임-공유-기능)
  - [선물 링크 등록 기능](#-선물-링크-등록-기능)
  - [선물 선정 토너먼트 기능](#-선물-선정-토너먼트-기능)
  - [마이페이지 기능](#-마이페이지-기능)
- [🍪 기술 스택](#️-기술-스택)
- [🦾 시스템 아키텍처](#️-시스템-아키텍처)
  - [폴더 구조](#-폴더-구조)
  - [아키텍처 구조](#-아키텍처-구조)
  - [ERD](#-erd)
  - [실행 방법](#-실행-방법)
- [🍰 Git & Code Convention](#-git--code-convention)
- [🍩 API Specification](#-api-specification)

<br />

## ✍🏻 프로젝트 개요
> ❤️ 33rd SOPT 웹잼 Team Sweet

스윗은 여러 명의 사람들이 모여 한 사람의 선물을 준비할 때, 모두의 의견을 모아 하나의 선물을 고르고 정성까지 전달하는 웹 서비스입니다.

<br />


## 🥰 팀원 소개
| [박지영(ziiyouth)](https://github.com/ziiyouth) | [송하연(hysong4u)](https://github.com/hysong4u) |
| :--------: | :--------: |
| <img src="https://github.com/ziiyouth.png" width="200px"/> | <img src="https://github.com/hysong4u.png" width="200px"/> |
| Back-end | Back-end |

<br />

## 🍭 핵심 기능

### 소셜 로그인 기능

> 카카오톡 소셜 로그인을 통해 간편한 회원가입이 가능합니다.

### 선물 준비 모임 개설 기능

> 이름, 썸네일, 토너먼트 기간 등의 정보 입력을 통해 선물 준비 모임을 개설합니다.

### 선물 준비 모임 공유 기능
> 초대 코드 링크를 통해, 혹은 카카오톡으로 간편한 초대가 가능합니다.

### 선물 선정 토너먼트 기능
> 선물 선정을 토너먼트 형식으로 흥미롭게 진행합니다. 마감 시간 후에 전체 순위를 확인할 수 있습니다.

### 마이페이지 기능
> 내가 준비했던/준비하고 있는 선물 모임을 찾아볼 수 있습니다.

</aside>
<br />

## 🍪 기술 스택
### 🖥 Backend

|역할|종류|
|-|-|
|Framework|<img alt="RED" src ="https://img.shields.io/badge/SPRING-6DB33F.svg?&style=for-the-badge&logo=Spring&logoColor=white"/> <img alt="RED" src ="https://img.shields.io/badge/SPRING Boot-6DB33F.svg?&style=for-the-badge&logo=SpringBoot&logoColor=white"/>|
|Database|<img alt="RED" src ="https://img.shields.io/badge/Mysql-003545.svg?&style=for-the-badge&logo=Mysql&logoColor=white"/>|
|Database Service|<img alt="RED" src ="https://img.shields.io/badge/Amazon Rds-527FFF.svg?&style=for-the-badge&logo=AmazonRds&logoColor=white"/>|
|Programming Language|<img alt="RED" src ="https://img.shields.io/badge/JAVA-004027.svg?&style=for-the-badge&logo=Jameson&logoColor=white"/>|
|API|![REST](https://img.shields.io/badge/Rest-4B3263?style=for-the-badge&logo=rest&logoColor=white)                                     
|Version Control|![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white) ![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white) |
|CICD|<img src="https://img.shields.io/badge/Docker-2496ED?&logo=Docker&style=for-the-badge&logoColor=white"> <img src="https://img.shields.io/badge/GitHub Actions-000000?logo=github-actions&style=for-the-badge">|
<br />

### 🖥 Common
|역할|종류|
|-|-|
|협업 관리|<img alt="RED" src ="https://img.shields.io/badge/Notion-000000.svg?&style=for-the-badge&logo=Notion&logoColor=white"/> |
|디자인|<img alt="RED" src ="https://img.shields.io/badge/Figma-F24E1E.svg?&style=for-the-badge&logo=Figma&logoColor=white"/>|
|소통|<img src="https://img.shields.io/badge/Discord-5865F2?logo=Discord&style=for-the-badge&logoColor=ffffff">

<br />


## 🦾 시스템 아키텍처


### 📂 폴더 구조
```
📂 src
┣ 📂 java.org.sopt.sweet
┃  ┣ 📂 domain
┃  ┃  ┣ 📂 sample
┃  ┃  ┃  ┣ 📂 controller
┃  ┃  ┃  ┣ 📂 entity
┃  ┃  ┃  ┣ 📂 dto
┃  ┃  ┃  ┃  ┣ 📂 request
┃  ┃  ┃  ┃  ┣ 📂 response
┃  ┃  ┃  ┣ 📂 service
┃  ┃  ┃  ┣ 📂 domain
┃  ┃  ┃  ┣ 📂 repository
┃  ┣ 📂 global
┃  ┃  ┣ 📂 common
┃  ┃  ┣ 📂 config
┃  ┃  ┃  ┣ 📂 auth
┃  ┃  ┣ 📂 error
┃  ┃  ┃  ┣ 📂 dto
┃  ┃  ┃  ┣ 📂 exception
┃  ┃  ┃  ┣ 📂 handler
┃  ┃  ┣ 📂 external
┣ 📂 resources
┃  ┣ application.yml
┣ SweetApplication.class
```

<br />

### 📂 아키텍처 구조
![image](https://github.com/SWEET-DEVELOPERS/sweet-server/assets/101448999/8dae9f9f-e99d-4b0f-a40a-5f34a4cb65eb)


<br />

### 📢 ERD
![sweet-database (1)](https://github.com/SWEET-DEVELOPERS/sweet-server/assets/101448999/c24a6bd9-f798-447f-9148-21b5f621da07)


<br />

### 📢 실행 방법
1. git clone을 진행한다.
2. resources 폴더에 application.yml을 추가한다.
3. build/libs 디렉터리로 이동해 java -jar sweet-0.0.1-SNAPSHOT.jar 명령어를 실행한다.

<br />

## 🍰 Git & Code Convention
### ✨ [ 스윗 서버의 코드 컨벤션 보러가기 (클릭!) ](https://walnut-stinger-e4f.notion.site/Server-Convention-2b3b398212204f819d2abd3480e915c5?pvs=4)
### ✨ [ 스윗 서버의 기술 스택 보러가기 (클릭!) ](https://walnut-stinger-e4f.notion.site/Server-Architecture-347bbc9ab1054dfc945d18cb4946d0bc?pvs=4)


## 🍩 API Specification
### ✨ [ 스윗 서버의 API 명세서 보러가기 (클릭!) ]()
