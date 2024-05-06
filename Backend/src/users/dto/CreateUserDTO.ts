import { ApiProperty } from "@nestjs/swagger"

export class CreateUserDTO {
    @ApiProperty()
    id: string
    @ApiProperty()
    username: string
    @ApiProperty()
    email:string 
    @ApiProperty()
    password: string
    @ApiProperty()
    confirmpassword: string
    @ApiProperty()
    authType: number
}