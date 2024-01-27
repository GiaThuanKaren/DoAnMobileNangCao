import { Controller, Get, Post, Body, Patch, Param, Delete } from '@nestjs/common';
import { UserPostService } from './user_post.service';
import { CreateUserPostDto } from './dto/create-user_post.dto';
import { UpdateUserPostDto } from './dto/update-user_post.dto';

@Controller('user-post')
export class UserPostController {
  constructor(private readonly userPostService: UserPostService) {}

  @Post()
  create(@Body() createUserPostDto: CreateUserPostDto) {
    return this.userPostService.create(createUserPostDto);
  }

  @Get()
  findAll() {
    return this.userPostService.findAll();
  }

  @Get(':id')
  findOne(@Param('id') id: string) {
    return this.userPostService.findOne(+id);
  }

  @Patch(':id')
  update(@Param('id') id: string, @Body() updateUserPostDto: UpdateUserPostDto) {
    return this.userPostService.update(+id, updateUserPostDto);
  }

  @Delete(':id')
  remove(@Param('id') id: string) {
    return this.userPostService.remove(+id);
  }
}
