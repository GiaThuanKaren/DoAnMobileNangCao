import { Body, Controller, Get, HttpStatus, Param, Post, Put, Delete } from '@nestjs/common';

import { CreateUserDTO } from '../../dto/CreateUserDTO';
import { UsersService } from '../../service/users/users.service';
import { UpdateUserDTO } from '../../dto/UpdateUserDto';
import { ApiTags } from '@nestjs/swagger';
import { MSG } from '../../../utils';

@Controller('users')
@ApiTags("User ")

export class UsersController {
    constructor(private userService: UsersService) {

    }
    @Get()
    async getusers() {
        return await this.userService.findUser()
    }

    @Get(":id")
    async findOne(@Param("id") id: number) {
        let result = await this.userService.findOneUser(id)
        return result
    }

    @Post()
    async createUser(@Body() createuserDTO: CreateUserDTO) {

        return await this.userService.createUser(createuserDTO)
    }

    @Put(':id')
    async updateUserByid(
        @Param("id") id: number,
        @Body() updateUserDTO: UpdateUserDTO
    ) {
        return await this.userService.updateUser(updateUserDTO, id)
    }

    @Delete(":id")
    async deleteUserbyId(
        @Param("id") id: number
    ) {
        return await this.userService.deleteUserbyId(id);
    }
}
