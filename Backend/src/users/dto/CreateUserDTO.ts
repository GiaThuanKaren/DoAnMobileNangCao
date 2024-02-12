import { ApiProperty } from "@nestjs/swagger"

export class CreateUserDTO {
    @ApiProperty()
    username: string
    @ApiProperty()
    @ApiProperty()
    password: string
    @ApiProperty()
    confirmpassword: string
    @ApiProperty()
    authType: number
}