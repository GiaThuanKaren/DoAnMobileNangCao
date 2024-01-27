import { Body, Controller, Get, Param, Post, Put } from '@nestjs/common';
import { CreateUserDTO } from 'src/users/dto/CreateUserDTO';
import { UpdateUserDTO } from 'src/users/dto/UpdateUserDto';
import { UsersService } from 'src/users/service/users/users.service';

@Controller('users')
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
