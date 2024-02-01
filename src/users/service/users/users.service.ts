import { HttpStatus, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';

import { Repository } from 'typeorm';
import { User } from '../../entites/user.entity';
import { CreateUserDTOParamas, UpdateUserDTOParamas } from '../../../utils/types';
import { AuthStrategy } from '../../../auth_strategy/entities/auth_strategy.entity';
import { CreateUserDTO } from '../../dto/CreateUserDTO';
import { MSG } from '../../../utils';

@Injectable()
export class UsersService {
    constructor(
        @InjectRepository(User) private userRepository: Repository<User>,
        @InjectRepository(AuthStrategy) private authStrategyRepository: Repository<AuthStrategy>

    ) {

    }

    async findUser() {
        let result = await this.userRepository.find()
        return MSG(
            HttpStatus.OK,
            result
        )
    }

    async findOneUser(id: number) {
        try {
            let result = await this.userRepository.findOne({
                where: {
                    id: id
                }
            })
            return MSG(
                HttpStatus.OK,
                result
            )
        } catch (error) {
            return MSG(
                HttpStatus.CONFLICT,

            )
        }
    }

    async createUser(createuserDetail: CreateUserDTO) {
        try {
            const foundAuthStrategy = await this.authStrategyRepository.findOne({
                where: {
                    id: createuserDetail.authType
                }
            })
            console.log(" Auth Strategy Found ", foundAuthStrategy)

            const newUser = this.userRepository.create({
                ...createuserDetail,
                createdAt: new Date(),
                authStrategy: foundAuthStrategy

            })
            let result = await this.userRepository.save(newUser)
            return MSG(
                HttpStatus.ACCEPTED,
                result
            )
        } catch (error) {
            return MSG(
                HttpStatus.CONFLICT,

            )
        }

    }

    async updateUser(updateUserDetail: UpdateUserDTOParamas, id: number) {
        // return await this.userRepository.findBy({
        //     id:id
        // })
        let result = await this.userRepository.update({
            id
        }, {
            ...updateUserDetail
        })
        return MSG(
            HttpStatus.ACCEPTED,
            result
        )
    }
}
