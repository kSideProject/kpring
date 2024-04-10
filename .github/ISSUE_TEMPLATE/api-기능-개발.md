---
name: API 기능 개발
about: API 상세 문서와 어떤 기능을 개발하는지를 관리합니다.
title: ''
labels: ''
assignees: ''

---

## 어떤 기능인가요?

> 기능에 대한 설명을 적어주세요.

### request

> 요청에 대한 상세 설명을 적어주세요.

#### URL & Method

`GET /path/{param}?query=1`

#### path param

| attribute | description      |
|-----------|------------------|
| param     | 속성에 대한 설명을 적어주세요 | 
|           |                  | 

#### query param

| attribute | description      |
|-----------|------------------|
| query     | 속성에 대한 설명을 적어주세요 | 
|           |                  | 

#### header

| header       | values           | description | 
|--------------|------------------|-------------| 
| Content-type | application/json |             | 
|              |                  |             | 

#### body

```
{
	"id": 유저 식별자,
	"nickname": 유저 닉네임
}
```

| attribute | description | type | 
|-----------|-------------|------| 
| id        | 유저 식별자      |      |
| nickname  | 유저 닉네임      |      | 
|           |             |      |

### response

> 응답에 대한 설명을 적어주세요

#### header

| header       | values           | description | 
|--------------|------------------|-------------| 
| Content-type | application/json |             | 
|              |                  |             | 

#### body

```
{
	"id": 유저 식별자,
	"nickname": 유저 닉네임
}
```

| attribute | description | type | 
|-----------|-------------|------| 
| id        | 유저 식별자      |      |
| nickname  | 유저 닉네임      |      | 
|           |             |      |

#### Error

| status                     | description | header | body | 
|----------------------------|-------------|--------|------| 
| 500(internal server error) | ..          | null   | null | 
| 400(bad request)           | ..          | null   | null |
|                            |             | null   | null |

## 작업 상세 내용

- [ ] todo
- [ ] todo
