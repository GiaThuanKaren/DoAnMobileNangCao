import { Controller, Get, Post, Body, Patch, Param, Delete, Query } from '@nestjs/common';
import { PostService } from './post.service';
import { CreatePostDto } from './dto/create-post.dto';
import { UpdatePostDto } from './dto/update-post.dto';
import { ApiTags } from '@nestjs/swagger';

@Controller('post')
@ApiTags("Post")
export class PostController {
  constructor(private readonly postService: PostService) { }

  @Post()
  create(

    @Body() createPostDto: CreatePostDto
  ) {
    console.log("User Id", createPostDto.idUser)
    return this.postService.create(createPostDto);
  }

  @Get()
  findAll() {
    return this.postService.findAll();
  }
  @Get("/parentPost/:id")
  async findAllParentPost(@Param('id') id?: number) {
    // return await this.postService.findAllParentPost(null)
    if (id == 0) {
      console.log(" Null parent iD")
      return await this.postService.findAllParentPost(0)

    }
    return await this.postService.findAllParentPost(id)

  }
  @Get(':id')
  findOne(@Param('id') id: string) {
    return this.postService.findOne(+id);
  }

  @Patch('update/:id')
  async update(@Param('id') id: string, @Body() updatePostDto: UpdatePostDto) {
    try {
      return await this.postService.update(+id, updatePostDto);

    } catch (error) {
      return error
    }
  }

  @Delete(':id')
  remove(@Param('id') id: string) {
    return this.postService.remove(+id);
  }
}
