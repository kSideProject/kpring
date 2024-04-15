## 어떤 기능인가요?

> 친구 추가 API 호출 시, 친구 리스트에 친구를 추가합니다.

### request

> 친구 추가를 위한 친구 아이디를 Request Body 의 JSON 형식으로 요청합니다.

#### URL & Method

`POST /users/friends/add/`

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
    "userId" : 1,
    // 본인 아이디
    // 이걸 넘겨야할까,,,ㅇㅅㅇ,, 세션/토큰이 로그인 정보를 들고 있을텐데,,,
	"freindIdList" : [ 2, 3 ,,, ]
	// 추가한 친구의 유저 아이디 
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
    "userId" : 1,
    // 본인 아이디
	"freindIdList" : [ 2, 3 ,,, ],
	// 추가한 친구의 유저 아이디 
	"message" : "친구 추가 성공!"
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
