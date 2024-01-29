import { ApiProperty } from "@nestjs/swagger"

export class CreatePostDto {
    @ApiProperty()
    title: string
    @ApiProperty()
    description: string
    @ApiProperty()
    icon: string
    @ApiProperty()
    coverImagelink: string
    @ApiProperty()
    parentId: number
}
