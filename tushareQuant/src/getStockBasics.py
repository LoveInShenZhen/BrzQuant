#!/usr/bin/env python3
# -*- coding:utf-8 -*-

import argparse
import json
import os
from datetime import date

import tushare as ts

parser = argparse.ArgumentParser(description='获取沪深上市公司基本情况')

"""
ArgumentParser.add_argument(name or flags...[, action]
                                            [, nargs]
                                            [, const]
                                            [, default]
                                            [, type]
                                            [, choices]
                                            [, required]
                                            [, help]
                                            [, metavar]
                                            [, dest])
Define how a single command-line argument should be parsed.
Each parameter has its own more detailed description below, but in short they are:

    name or flags - Either a name or a list of option strings, e.g. foo or -f, --foo.
    action - The basic type of action to be taken when this argument is encountered at the command line.
    nargs - The number of command-line arguments that should be consumed.
    const - A constant value required by some action and nargs selections.
    default - The value produced if the argument is absent from the command line.
    type - The type to which the command-line argument should be converted.
    choices - A container of the allowable values for the argument.
    required - Whether or not the command-line option may be omitted (optionals only).
    help - A brief description of what the argument does.
    metavar - A name for the argument in usage messages.
    dest - The name of the attribute to be added to the object returned by parse_args().

"""

parser.add_argument('-d', '--dir', help='保存 csv 数据的目录', default="/Users/kk/ssdwork/github/tuShareData", type=str)
parser.add_argument('--fake', help='不获取数据,仅仅返回数据文件路径', action='store_true', default=False)

args = parser.parse_args()


def main():
    res = {}

    try:
        today = date.today()
        fname = '%s.csv' % today.isoformat()
        output_dir = os.path.join(args.dir, "get_stock_basics")
        os.makedirs(output_dir, exist_ok=True)
        csvname = os.path.join(output_dir, fname)

        res['ret'] = 0
        res['msg'] = 'OK'
        res['csvpath'] = csvname

        if args.fake:
            print(json.dumps(res, sort_keys=True, indent=4))
            exit(0)
        else:
            df = ts.get_stock_basics()
            df.to_csv(path_or_buf=csvname, float_format='%.6f')

            if df.empty:
                res['ret'] = -1
                res['msg'] = 'No Data'

    except Exception as ex:
        res['ret'] = -1
        res['msg'] = str(ex)

    print(json.dumps(res, sort_keys=True, indent=4))
    exit(res['ret'])


if __name__ == '__main__':
    main()
