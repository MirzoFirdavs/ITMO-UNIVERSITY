#include "requests.h"

#include <algorithm>
#include <cmath>

unsigned to_decimal_system(unsigned y, const std::vector<unsigned char> & message)
{
    unsigned result = 0;
    for (int i = 0; i < 4; ++i) {
        result += message[y + i] << 8 * (3 - i);
    }

    return result;
}

bool is_nth_bit_zero(unsigned number, unsigned n)
{
    return ((number >> n) & 1) != 0;
}

LiquidityIndicator indicator(unsigned k)
{
    if (is_nth_bit_zero(k, 4)) {
        return LiquidityIndicator::None;
    }

    if (is_nth_bit_zero(k, 3)) {
        return LiquidityIndicator::Removed;
    }

    return LiquidityIndicator::Added;
}

std::string cl_ord_id_parse(const std::string & str)
{
    const unsigned right_idx = 13;
    if (str[right_idx] == ' ') {
        return std::string(str.begin(), str.begin() + str.find(' '));
    }

    return str;
}

ExecutionDetails decode_executed_order(const std::vector<unsigned char> & message)
{
    const unsigned cl_id_start = 9;
    const unsigned cl_id_end = 23;
    const unsigned volume_start = 23;
    const unsigned price_start = 27;
    const unsigned number_start = 32;
    const unsigned cnt_part_start = 36;
    const unsigned cnt_part_end = 40;
    const unsigned b_n = message[43];

    ExecutionDetails exec_details;

    exec_details.cl_ord_id = cl_ord_id_parse(std::string(message.begin() + cl_id_start, message.begin() + cl_id_end));
    exec_details.match_number = to_decimal_system(number_start, message);
    exec_details.filled_volume = to_decimal_system(volume_start, message);
    exec_details.price = round(to_decimal_system(price_start, message)) / 10000;
    exec_details.counterpart = std::string(message.begin() + cnt_part_start, message.begin() + cnt_part_end);
    exec_details.liquidity_indicator = indicator(b_n);
    exec_details.internalized = (is_nth_bit_zero(b_n, 5));
    exec_details.self_trade = (is_nth_bit_zero(b_n, 7));

    return exec_details;
}