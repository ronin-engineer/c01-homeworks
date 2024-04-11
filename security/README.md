# Security

## Requirements

- Authentication using JWT
- Authorization using role + permission based access control

## Implement

- Hardcode: Map<username, List<User>>
- API login: create JWT
- API Authorize:
    - Declare the permission for this API
    - Verify token, permission

## Demo
>
> [list apis](api.http)