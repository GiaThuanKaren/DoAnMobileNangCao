import { ApiProperty } from "@nestjs/swagger"

export class UpdateUserDTO {
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