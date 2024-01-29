import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { User } from 'src/users/entites/user.entity';
import { CreateUserDTOParamas, UpdateUserDTOParamas } from 'src/utils/types';
import { Repository } from 'typeorm';

@Injectable()
export class UsersService {
    constructor(
        @InjectRepository(User) private userRepository: Repository<User>) {

    }

    findUser() {
        return this.userRepository.find();
    }

    async createUser(createuserDetail: CreateUserDTOParamas) {
        const newUser = this.userRepository.create({
            ...createuserDetail,
            createdAt: new Date(),
        })
        return await this.userRepository.save(newUser)
    }

    async updateUser(updateUserDetail: UpdateUserDTOParamas, id: number) {
        // return await this.userRepository.findBy({
        //     id:id
        // })
        return await this.userRepository.update({
            id
        }, {
            ...updateUserDetail
        })
    }
}
