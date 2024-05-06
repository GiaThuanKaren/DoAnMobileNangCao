import { Controller, Get, Post, Body, Patch, Param, Delete } from '@nestjs/common';
import { UserPostService } from './user_post.service';
import { CreateUserPostDto } from './dto/create-user_post.dto';
import { UpdateUserPostDto } from './dto/update-user_post.dto';
import { ApiTags } from '@nestjs/swagger';

@Controller('user-post')
@ApiTags("User Post")

export class UserPostController {
  constructor(private readonly userPostService: UserPostService) { }

  @Get("/getAllPostByIdUser/:id/:parentid")
  async getAllPostByIdUser(
    @Param("id") idUser: number,
    @Param("parentid") parentid: number
  ) {
    if (parentid == -1)
      parentid = null
    console.log(
      idUser, "Id User Controller",
      parentid, "ParentId"
    )

    // return idUser
    return await this.userPostService.getAllPostByIdUser(idUser, parentid)
  }
  @Post()
  async create(@Body() createUserPostDto: CreateUserPostDto) {
    return await this.userPostService.create(createUserPostDto);
  }

  @Get()
  async findAll() {
    return await this.userPostService.findAll();
  }

  @Get(':idPost/:idUser')
  async findOne(
    @Param('idPost') idPost: number,
    @Param('idUser') idUser: number
  ) {
    return await this.userPostService.findOne(+idPost, +idUser);
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
