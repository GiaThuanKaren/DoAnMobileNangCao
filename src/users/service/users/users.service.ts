import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';

import { Repository } from 'typeorm';
import { User } from '../../entites/user.entity';
import { CreateUserDTOParamas, UpdateUserDTOParamas } from '../../../utils/types';

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
