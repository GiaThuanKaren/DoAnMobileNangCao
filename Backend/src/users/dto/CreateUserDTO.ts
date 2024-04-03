import { ApiProperty } from "@nestjs/swagger"

export class CreateUserDTO {
    // @ApiProperty()
    // id: number
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