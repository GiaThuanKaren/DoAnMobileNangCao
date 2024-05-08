import { HttpStatus, Injectable } from '@nestjs/common';
import { CreateUserPostDto } from './dto/create-user_post.dto';
import { UpdateUserPostDto } from './dto/update-user_post.dto';
import { InjectRepository } from '@nestjs/typeorm';
import { UserPost } from './entities/user_post.entity';
import { IsNull, Repository } from 'typeorm';
import { UserPermission } from '../user_permission/entities/user_permission.entity';
import { MSG } from '../utils';

@Injectable()
export class UserPostService {
  constructor(
    @InjectRepository(UserPost) private userPostRepository: Repository<UserPost>,
    @InjectRepository(UserPermission) private userPermissionRepository: Repository<UserPermission>
  ) {

  }

  async create(createUserPostDto: CreateUserPostDto) {
    try {
      const userPermissionFound = await this.userPermissionRepository.findOne({
        where: {
          id: createUserPostDto.userPermissionId
        }
      })
      const newUserPost = this.userPostRepository.create({
        ...createUserPostDto,
        userPermission: userPermissionFound,
        created_at: Date(),
      })

      const result = await this.userPostRepository.save(
        newUserPost
      )
      return MSG(
        HttpStatus.ACCEPTED,
        result
      );
    } catch (error) {
      return MSG(
        HttpStatus.CONFLICT,
        error
      )
    }

  }
  // user: true,
  //       userPermission: true,
  //       listPost: true
  async findAll() {
    const result = await this.userPostRepository.find({
      relations: {
        user: true,
        userPermission: true,
        // listPost:true
      },

    })
    return MSG(
      HttpStatus.OK,
      result
    );
  }

  async getAllPostByIdUser(userId: number, parentId = null) {
    if (parentId == null) {
      console.log("NUll ")
      let result = await this.userPostRepository.find({
        where: {
          user_id: userId,
          listPost: {
            parentId: IsNull()
          }
        },
        relations: {
          listPost: true
        }
      })
      return result
    }
    let result = await this.userPostRepository.find({
      where: {
        user_id: userId,
        listPost: {
          parentId: parentId
        }
      },
      relations: {
        listPost: true
      }
    })
    return result
  }


  async findOne(idPost: number, idUser: number) {

    return await this.userPostRepository.findOne({
      where: {
        post_id: idPost,
        user_id: idUser
      },
      relations: {
        user: true,
        userPermission: true,
        listPost: true


      }
    })
  }

  update(id: number, updateUserPostDto: UpdateUserPostDto) {
    return `This action updates a #${id} userPost`;
  }

  remove(id: number) {
    return `This action removes a #${id} userPost`;
  }
}
