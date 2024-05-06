import { Injectable, HttpStatus } from '@nestjs/common';
import { CreatePostDto } from './dto/create-post.dto';
import { UpdatePostDto } from './dto/update-post.dto';
import { InjectRepository } from '@nestjs/typeorm';
import { Post } from './entities/post.entity';
import { IsNull, Repository } from 'typeorm';
import { MSG } from '../utils';
import { UserPost } from '../user_post/entities/user_post.entity';
import { UserPermission } from '../user_permission/entities/user_permission.entity';

@Injectable()
export class PostService {
  constructor(
    @InjectRepository(Post) private postRepository: Repository<Post>,
    @InjectRepository(UserPost) private userPostRepository: Repository<UserPost>,
    @InjectRepository(UserPermission) private userPersmission: Repository<UserPermission>
  ) {

  }



  async create(createPostDto: CreatePostDto) {
    let result: Post | null = null
    if (createPostDto.parentId) {
      result = await this.postRepository.findOne({
        where: {
          id: createPostDto.parentId
        }
      })
      if (!result) {
        return MSG(
          HttpStatus.CONFLICT,
          "Invalid ParendId Post "
        )
      }
    }

    console.log("Parent Found ", result)
    let newPost = this.postRepository.create({
      ...createPostDto,
      parent: result,


    })
    await this.postRepository.save(newPost)
    console.log("Save Post Done")
    let userPersmisson = await this.userPersmission.findOne({
      where: {
        id: 1,

      }
    })
    let newUserPost = this.userPostRepository.create({
      user_id: createPostDto.idUser,
      post_id: newPost.id,
      created_at: new Date(),
      userPermission: userPersmisson
    })
    await this.userPostRepository.save(newUserPost)
    // newPost.id
    return MSG(
      HttpStatus.OK,
      {...newUserPost,...newPost}
    );
  }




  async findAll() {
    let result = await this.postRepository.find({

      relations: {
        parent: true
      }
    })
    return MSG(
      HttpStatus.OK,
      result
    );
  }
  async findAllParentPost(id: number) {
    let result = await this.postRepository.find({
      where: {
        // parentId: 0,
        parent: id == 0 ? IsNull() : {
          id
        }



      },
      relations: {
        parent: true


      }
    })
    return MSG(
      HttpStatus.OK + "slkdjfklsdjf",
      result
    );
  }

  async findOne(id: number) {
    let result = await this.postRepository.findOne({
      where: {
        id
      },
      relations: {
        parent: true,
        children: true
      }

    })
    return MSG(
      HttpStatus.OK,
      result
    );
  }

  async update(id: number, updatePostDto: UpdatePostDto) {
    let result = await this.postRepository.update({
      id
    }, {
      ...updatePostDto
    })
    return MSG(
      HttpStatus.OK,
      result
    )
  }

  async remove(id: number) {
    await this.userPostRepository.delete({
      post_id: id
    })
    let result = await this.postRepository.delete({
      id
    });
    return MSG(
      HttpStatus.OK,
      result
    )
  }
}


