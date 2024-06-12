<img src="https://github.com/blueme0/Chaeda/assets/81468180/f2e51a54-82ab-47ca-b9e6-adeaec2524e8" width="100" height="100" alt="ic_launcher-playstore">

# Chaeda
> 수학 학습에서의 취약점 분석과 복습 `repo:android`

[![채다 시연 영상](http://img.youtube.com/vi/nxwHWNVvW4Y/0.jpg)](https://youtu.be/nxwHWNVvW4Y?t=0s) 

> 클릭하면 시연 영상으로 이동합니다.

<br>

## Tech Stack ⚒️
- Clean Architecture
- Multi-Module

- MVVM
- Repository Pattern

- Compose + XML
- Hilt

- Coroutine
- Version Catalog

- Kotlin - Flow
- GitHub Action CI / CD

<br>

## System Architecture 💻

### Data Flow
![image](https://github.com/depromeet/TeumTeum-Android/assets/100370200/b914bf6d-d052-46be-ba05-a841673f38c0)

### Module
```
🗃️app
 ┣ 📂di
 ┣ 📂presentation
 ┣ 📂util
🗃️build-logic
 ┣ 📂convention
🗃️core
 ┣ 📂base
🗃️data
 ┣ 📂datasource.remote
 ┣ 📂interceptor
 ┣ 📂local
 ┣ 📂model
 ┃ ┣ 📂request
 ┃ ┣ 📂response
 ┣ 📂repository
 ┣ 📂service
🗃️domain
 ┣ 📂entity
 ┣ 📂enumSet
 ┣ 📂repository
```
