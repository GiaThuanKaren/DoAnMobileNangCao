import { PartialType } from '@nestjs/mapped-types';
import { CreateUserPostDto } from './create-user_post.dto';

export class UpdateUserPostDto extends PartialType(CreateUserPostDto) {}
