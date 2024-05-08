import { ApiProperty } from "@nestjs/swagger";
import { UserPost } from "../../user_post/entities/user_post.entity";

export class CreateUserPermissionDto {
    // @ApiProperty()
    // name: string
    @ApiProperty()
    postId: number
    @ApiProperty()
    name_permission:string
    // postId: number
}
