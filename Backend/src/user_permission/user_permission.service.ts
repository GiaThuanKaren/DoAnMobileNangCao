import { HttpStatus, Injectable } from '@nestjs/common';
import { CreateUserPermissionDto } from './dto/create-user_permission.dto';
import { UpdateUserPermissionDto } from './dto/update-user_permission.dto';
import { InjectRepository } from '@nestjs/typeorm';
import { UserPermission } from './entities/user_permission.entity';
import { Repository } from 'typeorm';
import { MSG } from '../utils';

@Injectable()
export class UserPermissionService {
  constructor(
    @InjectRepository(UserPermission) private userPermission: Repository<UserPermission>
  ) {

  }
  async create(createUserPermissionDto: CreateUserPermissionDto) {
    // let result = 
    const newUSerPermission = this.userPermission.create({
      ...createUserPermissionDto,

      postId: null

    })
    const result = await this.userPermission.save(
      newUSerPermission
    )
    return MSG(
      HttpStatus.OK,
      result
    );
  }

  async findAll() {
    const result = await this.userPermission.find()
    return MSG(
      HttpStatus.OK,
      result
    );
  }

  async findOne(id: number) {
    const result = await this.userPermission.findOne({
      where: {
        id
      },
      
      
    })
    return MSG(
      HttpStatus.OK,
      result
    );
  }

  async update(id: number, updateUserPermissionDto: UpdateUserPermissionDto) {
    return `This action updates a #${id} userPermission`;
  }

  async remove(id: number) {
    return `This action removes a #${id} userPermission`;
  }
}
