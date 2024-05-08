import { ApiProperty } from "@nestjs/swagger"

export class CreateUserPostDto {
    @ApiProperty()
    user_id: string
    @ApiProperty()
    post_id: number
    @ApiProperty()
    userPermissionId: number
}
