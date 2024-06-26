import { PartialType } from '@nestjs/mapped-types';
import { CreatePostDto } from './create-post.dto';
import { ApiProperty } from '@nestjs/swagger';

export class UpdatePostDto extends PartialType(CreatePostDto) {
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
