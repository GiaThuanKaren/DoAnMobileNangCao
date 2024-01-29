import { Body, Controller, Get, Param, Post, Put } from '@nestjs/common';

import { CreateUserDTO } from '../../dto/CreateUserDTO';
import { UsersService } from '../../service/users/users.service';
import { UpdateUserDTO } from '../../dto/UpdateUserDto';
import { ApiTags } from '@nestjs/swagger';

@Controller('users')
@ApiTags("User ")

export class UsersController {
    constructor(private userService: UsersService) {

    }
    @Get()
    async getusers() {
        return await this.userService.findUser()
    }


    @Post()
    async createUser(@Body() createuserDTO: CreateUserDTO) {
        const {
            confirmpassword,
            ...userDetail
        } = createuserDTO
        return await this.userService.createUser(userDetail)
    }

    @Put(':id')
    async updateUserByid(
        @Param("id") id: number,
        @Body() updateUserDTO:UpdateUserDTO
    ) {
      return await  this.userService.updateUser(updateUserDTO,id)
    }

}
