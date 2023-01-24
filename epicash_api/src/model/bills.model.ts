import { Table, Column, DataType, Model, HasMany } from 'sequelize-typescript'

@Table
export class Bills extends Model {
  
  @Column({
    allowNull: false,
    unique: false,
    type: DataType.FLOAT,
  })
  total: number;
  
  @Column({
    allowNull: false,
    unique: true,
    type: DataType.TEXT,
  })
  reference: string;
  
} 

