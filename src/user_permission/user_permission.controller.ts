import { Controller, Get, Post, Body, Patch, Param, Delete } from '@nestjs/common';
import { UserPermissionService } from './user_permission.service';
import { CreateUserPermissionDto } from './dto/create-user_permission.dto';
import { UpdateUserPermissionDto } from './dto/update-user_permission.dto';
import { ApiTags } from '@nestjs/swagger';

@Controller('user-permission')
@ApiTags("User Permisson")
export class UserPermissionController {
  constructor(private readonly userPermissionService: UserPermissionService) {

  }

  @Post()
  async create(@Body() createUserPermissionDto: CreateUserPermissionDto) {
    return await this.userPermissionService.create(createUserPermissionDto);
  }

  @Get()
  async findAll() {
    return await this.userPermissionService.findAll();
  }

  @Get(':id')
  async findOne(@Param('id') id: string) {
    return await this.userPermissionService.findOne(+id);
  }

  @Patch(':id')
  async update(@Param('id') id: string, @Body() updateUserPermissionDto: UpdateUserPermissionDto) {
    return await this.userPermissionService.update(+id, updateUserPermissionDto);
  }

  @Delete(':id')
  async remove(@Param('id') id: string) {
    return await this.userPermissionService.remove(+id);
  }
}
