import { Injectable } from '@nestjs/common';
import { CreatePostDto } from './dto/create-post.dto';
import { UpdatePostDto } from './dto/update-post.dto';
import { InjectRepository } from '@nestjs/typeorm';
import { Post } from './entities/post.entity';
import { Repository } from 'typeorm';

@Injectable()
export class PostService {
  constructor(
    @InjectRepository(Post) private postRepository: Repository<Post>
  ) {

  }
  create(createPostDto: CreatePostDto) {
    let newPost = this.postRepository.create({
      ...createPostDto,


    })
    return this.postRepository.save(
      newPost
    );
  }

  findAll() {
    return this.postRepository.find();
  }

  findOne(id: number) {
    return this.postRepository.findOne({
      where: {
        id
      }
    });
  }

  update(id: number, updatePostDto: UpdatePostDto) {

    return this.postRepository.update({
      id
    }, {
      ...updatePostDto
    })
  }

  remove(id: number) {
    return this.postRepository.delete({
      id
    });
  }
}
