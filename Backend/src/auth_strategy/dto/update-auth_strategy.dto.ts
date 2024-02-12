import { PartialType } from '@nestjs/mapped-types';
import { CreateAuthStrategyDto } from './create-auth_strategy.dto';
import { ApiProperty } from '@nestjs/swagger';

export class UpdateAuthStrategyDto extends PartialType(CreateAuthStrategyDto) {
    @ApiProperty()
    name:string 
}
