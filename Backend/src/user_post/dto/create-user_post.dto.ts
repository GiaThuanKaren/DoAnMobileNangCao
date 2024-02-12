import { ApiProperty } from "@nestjs/swagger"

export class CreateUserPostDto {
    @ApiProperty()
    user_id: number
    @ApiProperty()
    post_id: number
    @ApiProperty()
    userPermissionId: number
}
